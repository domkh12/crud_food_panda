package model.dto;

import lombok.Builder;
import model.entity.Customer;
import model.entity.Product;

import java.util.Date;
import java.util.List;

@Builder
public record OrderDto(
        Integer id,
        String customerName,
        String orderName,
        String orderDescription,
        Date orderedAt
) {
}
