package ru.Koristos;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class RepoClient implements Runnable {
    private final int port;
    private final String host;
    private SocketChannel curChannel;
    private final ContextData contextData;
    private final ClientPathHolder pathHolder;
    private final Consumer<ContextData> reader;
    private final Consumer<Boolean> closeChannel;
    private static final Logger LOGGER = LogManager.getLogger(RepoClient.class);
    static final Marker toCon = MarkerManager.getMarker("CONS");
    ClientCommandReader comReader;

    /**
     * Central hub for server connection.
     *
     * @param host       - server host to init connection.
     * @param port       - server port to init connection.
     * @param conData    - data container, having all the information to send to server.
     * @param pathHolder - information holder, used by channel handlers to get repo path and to respond to server.
     * @param comReader  - server message interpreter.
     * @param addThread  - Consumer to add threads to Executor Service.
     */


    RepoClient(String host, int port, ContextData conData, ClientPathHolder pathHolder,
               ClientCommandReader comReader, Consumer<Runnable> addThread) {
        this.port = port;
        this.host = host;
        this.contextData = conData;
        this.pathHolder = pathHolder;
        this.comReader = comReader;
        this.reader = (context) -> {
            contextData.clone(context);
            addThread.accept(comReader);
        };
        this.closeChannel = a -> {
            System.out.println("Connection lost...");
            close();
        };

    }

    /**
     * Starts client connection, according to supplied @param. Netty based.
     */

    @Override
    public void run() {

        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(this.host, this.port))
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            curChannel = socketChannel;
                            System.out.println("Connecting to server...");

                            socketChannel.pipeline().addLast(
                                    new DelimiterBasedFrameDecoder(2048, contextData.getDelimiter()));
                            socketChannel.pipeline().addLast(new RepoDecoderClient(reader, closeChannel));
                            socketChannel.pipeline().addLast(new RepoEncoder(contextData));
                            socketChannel.pipeline().addLast(new ChunkedWriteHandler());
                            socketChannel.pipeline().addLast(new IncomingFileHandler(pathHolder));
                        }
                    });
            LOGGER.info("Trying to connect server...");
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            LOGGER.error(toCon, "SWW with connection.", LOGGER.throwing(e));
        } finally {
            try {
                workGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                LOGGER.error(toCon, "SWW with connection closing.", LOGGER.throwing(e));
            }
        }

    }

    /**
     * Takes all the info from supplied ContextData and puts it in the buffer ContextData, where it's supposed to be
     * taken from by Channel Handlers. If a file supposed to be sent, it will be sent by chunks.
     *
     * @param context incoming context for the channel output.
     */

    public synchronized void execute(ContextData context) {
        if (!(this.curChannel.isActive())) this.pathHolder.channelNotActive();
        this.contextData.clone(context);
        if (context.getCommand() == CommandList.upload.getNum()) {
            try {
                this.contextData.clone(context);
                File toCheck = Paths.get(context.getFilePath()).toFile();
                ChunkedFile chunk = new ChunkedFile(toCheck, 1024);
                this.curChannel.writeAndFlush(chunk);
                this.contextData.setFilePath(toCheck.getName());
                wait();
                chunk.close();

            } catch (Exception e) {
                LOGGER.error(toCon, "SWW with writing in channel.", LOGGER.throwing(e));
            }
        } else this.curChannel.writeAndFlush(new byte[1]);
    }

    /**
     * Closes the channel.
     */

    public void close() {
        System.out.println("Connection closed.");
        this.curChannel.close();
        LOGGER.info("Connection closed.");
    }

    /**
     * Notifies the execute method to release the file sent.
     */

    public synchronized void goOn() {
        notify();
    }


}


