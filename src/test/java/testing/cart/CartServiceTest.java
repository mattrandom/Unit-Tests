package testing.cart;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import testing.order.Order;
import testing.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Test
    void processCartShouldSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandlerMock = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandlerMock);

        given(cartHandlerMock.canHandleCart(cart)).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandlerMock).sendToPrepare(cart);
        then(cartHandlerMock).should().sendToPrepare(cart);

        verify(cartHandlerMock, times(1)).sendToPrepare(cart);
        verify(cartHandlerMock, atLeastOnce()).sendToPrepare(cart);

        InOrder inOrder = inOrder(cartHandlerMock);
        inOrder.verify(cartHandlerMock).canHandleCart(cart);
        inOrder.verify(cartHandlerMock).sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void processCartShouldBeRejected() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandlerMock = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandlerMock);

        given(cartHandlerMock.canHandleCart(cart)).willReturn(false);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandlerMock, times(0)).sendToPrepare(cart);
        verify(cartHandlerMock, never()).sendToPrepare(cart);
        then(cartHandlerMock).should(never()).sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void processCartShouldNotSendToPrepareWithArgumentMatchers() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandlerMock = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandlerMock);

        given(cartHandlerMock.canHandleCart(any(Cart.class))).willReturn(false);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        verify(cartHandlerMock, never()).sendToPrepare(any(Cart.class));
        then(cartHandlerMock).should(never()).sendToPrepare(any(Cart.class));

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
    }

    @Test
    void canHandleCartShouldReturnMultipleValues() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandlerMock = mock(CartHandler.class);

        given(cartHandlerMock.canHandleCart(cart)).willReturn(true, false, false, true);

        //then
        assertThat(cartHandlerMock.canHandleCart(cart), equalTo(true));
        assertThat(cartHandlerMock.canHandleCart(cart), equalTo(false));
        assertThat(cartHandlerMock.canHandleCart(cart), equalTo(false));
        assertThat(cartHandlerMock.canHandleCart(cart), equalTo(true));
    }

    @Test
    void processCartShouldSendToPrepareWithLambdas() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandlerMock = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandlerMock);

        given(cartHandlerMock.canHandleCart(argThat(c -> c.getOrders().size() > 0))).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandlerMock).should().sendToPrepare(cart);

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void canHandleCartShouldThrowException() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandlerMock = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandlerMock);

        given(cartHandlerMock.canHandleCart(cart)).willThrow(IllegalStateException.class);

        //when
        //then
        assertThrows(IllegalStateException.class, () -> cartService.processCart(cart));
    }

    @Test
    void processCartShouldSendToPrepareWithArgumentCaptor() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandlerMock = mock(CartHandler.class);
        CartService cartService = new CartService(cartHandlerMock);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

        given(cartHandlerMock.canHandleCart(cart)).willReturn(true);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandlerMock).should().sendToPrepare(argumentCaptor.capture());
        verify(cartHandlerMock).sendToPrepare(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getOrders().size(), equalTo(1));

        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }
}