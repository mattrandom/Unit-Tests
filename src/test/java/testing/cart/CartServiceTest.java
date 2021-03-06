package testing.cart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import testing.order.Order;
import testing.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private CartHandler cartHandlerMock;
    @Captor
    private ArgumentCaptor<Cart> argumentCaptor;

    @Test
    void processCartShouldSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

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

    @Test
    void shouldDoNothingWhenProcessCart() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        given(cartHandlerMock.canHandleCart(cart)).willReturn(true);

//        doNothing().when(cartHandlerMock).sendToPrepare(cart);
        willDoNothing().given(cartHandlerMock).sendToPrepare(cart);
//        willDoNothing().willThrow(IllegalStateException.class).given(cartHandlerMock).sendToPrepare(cart);

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandlerMock).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders(), hasSize(1));
        assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
    }

    @Test
    void shouldAnswerWhenProcessCart() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

//        doAnswer(invocationOnMock -> {
//            Cart argumentCart = invocationOnMock.getArgument(0);
//            argumentCart.clearCart();
//            return true;
//        }).when(cartHandlerMock).canHandleCart(cart);
//
//        when(cartHandlerMock.canHandleCart(cart)).then(invocation -> {
//            Cart argumentCart = invocation.getArgument(0);
//            argumentCart.clearCart();
//            return true;
//        });
//
//        willAnswer(invocationOnMock -> {
//            Cart argumentCart = invocationOnMock.getArgument(0);
//            argumentCart.clearCart();
//            return true;
//        }).given(cartHandlerMock).canHandleCart(cart);

        given(cartHandlerMock.canHandleCart(cart)).will(invocation -> {
            Cart argumentCart = invocation.getArgument(0);
            argumentCart.clearCart();
            return true;
        });

        //when
        Cart resultCart = cartService.processCart(cart);

        //then
        then(cartHandlerMock).should().sendToPrepare(cart);
        assertThat(resultCart.getOrders().size(), equalTo(0));
    }

    @Test
    void deliveryShouldBeFree() {
        //given
        Cart cart = new Cart();
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());
        cart.addOrderToCart(new Order());

        given(cartHandlerMock.isDeliveryFree(cart)).willCallRealMethod();
//        doCallRealMethod().when(cartHandlerMock).isDeliveryFree(cart);

        //when
        boolean isDeliveryFree = cartHandlerMock.isDeliveryFree(cart);

        //then
        assertTrue(isDeliveryFree);
    }
}