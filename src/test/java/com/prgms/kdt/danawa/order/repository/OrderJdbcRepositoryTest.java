package com.prgms.kdt.danawa.order.repository;

import com.prgms.kdt.danawa.JdbcTestConfig;
import com.prgms.kdt.danawa.generic.domain.Address;
import com.prgms.kdt.danawa.generic.domain.Email;
import com.prgms.kdt.danawa.generic.domain.PostCode;
import com.prgms.kdt.danawa.order.domain.Order;
import com.prgms.kdt.danawa.order.domain.OrderStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(JdbcTestConfig.class)
@Sql(scripts = "classpath:schema.sql")
class OrderJdbcRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void init() {
        orderRepository.deleteAll();
    }

    @DisplayName("고객 아이디로 주문 내역들을 조회할 수 있다.")
    @Test
    void findOrdersByCustomerId() {
        // given
        Order order1 = new Order(1, 2, new Email("dlxortmd@gmail.com"), new Address("강송로 154"), new PostCode("10231"), OrderStatus.ORDERED, LocalDateTime.now());
        Order order2 = new Order(1, 4, new Email("dlxortmd123@gmail.com"), new Address("강송로 175"), new PostCode("10212"), OrderStatus.PAYED, LocalDateTime.now());
        Order insertedOrder1 = orderRepository.insert(order1);
        Order insertedOrder2 = orderRepository.insert(order2);

        // when
        List<Order> ordersByCustomerId = orderRepository.findOrdersByCustomerId(1);

        // then
        assertThat(List.of(insertedOrder1, insertedOrder2))
                .isEqualTo(ordersByCustomerId);
    }

    @DisplayName("주문 번호로 주문을 조회할 수 있다.")
    @Test
    void findById() {
        // test
        Order order1 = new Order(1, 2, new Email("dlxortmd@gmail.com"), new Address("강송로 154"), new PostCode("10231"), OrderStatus.ORDERED, LocalDateTime.now());
        Order insertedOrder1 = orderRepository.insert(order1);

        // when
        Optional<Order> maybeOrder = orderRepository.findById(insertedOrder1.getOrderId());

        // then
        assertThat(maybeOrder.get())
                .isEqualTo(insertedOrder1);
    }

    @DisplayName("등록되지 않은 주문은 조회할 수 없다.")
    @Test
    void findByIdEmpty() {
        // test
        int unknownOrderId = 11111;

        // when
        Optional<Order> maybeOrder = orderRepository.findById(unknownOrderId);

        // then
        assertThat(maybeOrder)
                .isEqualTo(Optional.empty());
    }

    @DisplayName("주문을 저장할 수 있다.")
    @Test
    void insert() {
        // test
        Order order1 = new Order(1, 2, new Email("dlxortmd@gmail.com"), new Address("강송로 154"), new PostCode("10231"), OrderStatus.ORDERED, LocalDateTime.now());

        // when
        Order insertedOrder1 = orderRepository.insert(order1);
        Optional<Order> maybeOrder = orderRepository.findById(insertedOrder1.getOrderId());

        // then
        assertThat(maybeOrder.get())
                .isEqualTo(insertedOrder1);
    }

    @DisplayName("주문을 수정할 수 있다.")
    @Test
    void update() {
        // given
        Order order1 = new Order(1, 2, new Email("dlxortmd@gmail.com"), new Address("강송로 154"), new PostCode("10231"), OrderStatus.ORDERED, LocalDateTime.now());
        Order insertedOrder1 = orderRepository.insert(order1);

        // when
        insertedOrder1.pay();
        Order updatedOrder = orderRepository.update(insertedOrder1);

        // then
        assertThat(updatedOrder.getOrderStatus())
                .isEqualTo(OrderStatus.PAYED);

    }

    @DisplayName("주문을 삭제할 수 있다.")
    @Test
    void delete() {
        // given
        Order order1 = new Order(1, 2, new Email("dlxortmd@gmail.com"), new Address("강송로 154"), new PostCode("10231"), OrderStatus.ORDERED, LocalDateTime.now());
        Order insertedOrder1 = orderRepository.insert(order1);

        // when
        orderRepository.delete(insertedOrder1.getOrderId());
        Optional<Order> maybeOrder = orderRepository.findById(insertedOrder1.getOrderId());

        // then
        assertThat(maybeOrder)
                .isEqualTo(Optional.empty());
    }
}