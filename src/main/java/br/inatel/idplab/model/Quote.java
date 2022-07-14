package br.inatel.idplab.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity(name = "quote")
public class Quote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Stock stock;
	
	@NotNull(message = "é obrigatório inserir uma data não nula")
	private LocalDate date;
	
	@NotNull
	@Positive(message = "valor não pode ser negativo ou zero")
	private BigDecimal price;

	public Quote() {
	}
	
	public Quote(Stock stock ,LocalDate date, BigDecimal price) {
		this.stock = stock;
		this.date = date;
		this.price = price;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, price, stock);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quote other = (Quote) obj;
		return Objects.equals(date, other.date) && Objects.equals(id, other.id) && Objects.equals(price, other.price)
				&& Objects.equals(stock, other.stock);
	}
}
