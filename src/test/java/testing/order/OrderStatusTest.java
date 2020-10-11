package testing.order;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import testing.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

class OrderStatusTest {

    @ParameterizedTest
    @EnumSource(OrderStatus.class)
    void allOrderStatusShouldBeShorterThan15Chars(OrderStatus orderStatus) {
        //given & when
        int charsLength = orderStatus.toString().length();
        //then
        assertThat(charsLength, lessThan(15));
        assertThat(charsLength, greaterThan(3));
    }
}