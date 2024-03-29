package ru.geekbrains.micecreator.dto.complex;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.complex.prototype.IdPositive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RegionEventDto implements IdPositive {

	private Integer id;
	private LocalDate eventDate;
	private Integer pax;
	private BigDecimal price;
	private BigDecimal nettoPrice;
	private Integer tourId;
	private ListItemDto service;
	private BigDecimal total;
	private BigDecimal nettoTotal;
	private LocalDate creationDate;
}
