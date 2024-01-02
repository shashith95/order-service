package com.learning.orderservice.mapper;

import com.learning.orderservice.model.entity.Order;
import com.learning.orderservice.model.request.OrderRequest;
import com.learning.orderservice.model.response.OrderResponse;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface EntityDtoMapper {

    OrderResponse orderEntityToDto(Order order);

    List<OrderResponse> orderEntityListToDtoList(Page<Order> orderList);

    Order orderDtoToEntity(OrderRequest orderRequest);

    /**
     * MapStruct will automatically use the map method when converting Optional<Long> to Long.
     *
     * @param value the optional value to map
     * @return the mapped Long value, or null if the optional is empty
     */
    default Long map(Optional<Long> value) {
        return value.orElse(null);
    }
}
