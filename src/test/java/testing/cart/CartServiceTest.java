package testing.cart;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.verification.VerificationMode;
import testing.order.Order;
import testing.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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
}