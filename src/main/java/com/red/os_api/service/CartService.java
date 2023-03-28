package com.red.os_api.service;

import com.red.os_api.entity.Cart;
import com.red.os_api.entity.CartKey;
import com.red.os_api.entity.req_resp.CartRequest;
import com.red.os_api.entity.req_resp.CartResponse;
import com.red.os_api.repository.AuthRepository;
import com.red.os_api.repository.CartRepository;
import com.red.os_api.repository.ProductRepository;
import com.red.os_api.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final AuthRepository authRepository;

    private final ProductRepository productRepository;

    private final JwtService jwtService;

    private final TokenRepository tokenRepository;



    public ResponseEntity<CartResponse> insertCartProduct(CartRequest cartRequest,Boolean update, @NonNull HttpServletRequest request,
                                                          @NonNull HttpServletResponse response,
                                                          @NonNull FilterChain filterChain){
        Cart cart = new Cart();
        try{
            cart = convertToEntity(cartRequest,update,request,response,filterChain);
            cartRepository.save(cart);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("The cart was successfully populated");
        return new ResponseEntity<>(convertToResponse(cart),HttpStatus.OK);
    }

    public ResponseEntity<List<CartResponse>> insertCartProduct(List<CartRequest> cartRequestList, Boolean update, @NonNull HttpServletRequest request,
                                                                @NonNull HttpServletResponse response,
                                                                @NonNull FilterChain filterChain){
        List<Cart> carts = new ArrayList<>();
        try{
            carts = convertToEntity(cartRequestList,update,request,response,filterChain);
            cartRepository.saveAll(carts);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("The cart items were successfully inserted");
        return new ResponseEntity<>(convertToResponse(carts),HttpStatus.OK);
    }


    public ResponseEntity<String> deleteProductFromCart(Integer id, @NonNull HttpServletRequest request,
                                                        @NonNull HttpServletResponse response,
                                                        @NonNull FilterChain filterChain){
        CartKey cartKey = new CartKey();
        try{
            cartKey.setAuth_id(getUserId(request,response,filterChain));
            cartKey.setProduct_id(id);
            cartRepository.deleteById(cartKey);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The product was deleted from the cart");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<CartResponse> getProductFromCartById(Integer id, @NonNull HttpServletRequest request,
                                                               @NonNull HttpServletResponse response,
                                                               @NonNull FilterChain filterChain){
        CartKey cartKey = new CartKey();
        Cart cart = new Cart();
        try{
            cartKey.setAuth_id(getUserId(request,response,filterChain));
            cartKey.setProduct_id(id);
            cart = cartRepository.getReferenceById(cartKey);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The product was retrieved from the cart");
        return new ResponseEntity<>(convertToResponse(cart),HttpStatus.OK);
    }


    public ResponseEntity<List<CartResponse>> getAllFromCart(@NonNull HttpServletRequest request,
                                                               @NonNull HttpServletResponse response,
                                                               @NonNull FilterChain filterChain){
        List<Cart> cartList = new ArrayList<>();
        try{

            cartList = cartRepository.findAllByAuth(authRepository.getReferenceById(getUserId(request,response,filterChain)));
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("The content was retrieved from the cart");
        return new ResponseEntity<>(convertToResponse(cartList),HttpStatus.OK);
    }


    private Cart convertToEntity(CartRequest cartRequest, Boolean update, @NonNull HttpServletRequest request,
                                 @NonNull HttpServletResponse response,
                                 @NonNull FilterChain filterChain) throws NoSuchFieldException, ServletException, IOException {
        Cart cart = new Cart();
        Integer auth_id = getUserId(request,response,filterChain);
        CartKey cartKey = new CartKey();
        if(!isProductExist(cartRequest.getProduct_id()))
            throw new NoSuchFieldException("Such product id or/and account id doesn't exist");
        cartKey.setProduct_id(cartRequest.getProduct_id());
        cartKey.setAuth_id(auth_id);
        cart.setCartKey(cartKey);
        cart.setProduct(productRepository.getReferenceById(cartRequest.getProduct_id()));
        cart.setAuth(authRepository.getReferenceById(auth_id));
        cart.setQuantity(cartRequest.getQuantity());
        if(cartRepository.existsById(cartKey)&&!update) cart.setQuantity(cart.getQuantity()+cartRepository.getReferenceById(cartKey).getQuantity());
        return cart;
    }

    private List<Cart> convertToEntity(List<CartRequest> cartRequestList,Boolean update, @NonNull HttpServletRequest request,
                                 @NonNull HttpServletResponse response,
                                 @NonNull FilterChain filterChain) throws NoSuchFieldException, ServletException, IOException {
        List<Cart> carts = new ArrayList<>();
        Integer auth_id = getUserId(request,response,filterChain);
        for(CartRequest cartRequest: cartRequestList){
        Cart cart = new Cart();
        CartKey cartKey = new CartKey();
        if(!isProductExist(cartRequest.getProduct_id()))
            throw new NoSuchFieldException("Such product id or/and account id doesn't exist");
        cartKey.setProduct_id(cartRequest.getProduct_id());
        cartKey.setAuth_id(auth_id);
        cart.setCartKey(cartKey);
        cart.setProduct(productRepository.getReferenceById(cartRequest.getProduct_id()));
        cart.setAuth(authRepository.getReferenceById(auth_id));
        cart.setQuantity(cartRequest.getQuantity());
        if(cartRepository.existsById(cartKey)&&!update) cart.setQuantity(cart.getQuantity()+cartRepository.getReferenceById(cartKey).getQuantity());
        carts.add(cart);
        }
        return carts;
    }

    private CartResponse convertToResponse(Cart cart){
        CartResponse cartResponse = new CartResponse();
        cartResponse.setAuth_id(cart.getAuth().getId());
        cartResponse.setEmail(cart.getAuth().getEmail());
        cartResponse.setProduct_id(cart.getProduct().getProduct_id());
        cartResponse.setProductName(cart.getProduct().getProduct_name());
        cartResponse.setQuantity(cart.getQuantity());
        return cartResponse;
    }

    private List<CartResponse> convertToResponse(List<Cart> cartList){
        List<CartResponse> cartResponseList = new ArrayList<>();
        for(Cart cart:cartList) {
            CartResponse cartResponse = new CartResponse();
            cartResponse.setAuth_id(cart.getAuth().getId());
            cartResponse.setEmail(cart.getAuth().getEmail());
            cartResponse.setProduct_id(cart.getProduct().getProduct_id());
            cartResponse.setProductName(cart.getProduct().getProduct_name());
            cartResponse.setQuantity(cart.getQuantity());
            cartResponseList.add(cartResponse);
        }
        return cartResponseList;
    }


    private Integer getUserId(@NonNull HttpServletRequest request,
                                 @NonNull HttpServletResponse response,
                                 @NonNull FilterChain filterChain) throws ServletException, IOException, NoSuchFieldException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            throw new IllegalArgumentException();
        }
        if(!tokenRepository.existsTokenByTokenAndRevokedAndExpired(authHeader
                .substring(7),false,false)) throw new NoSuchFieldException();

        return authRepository.findByEmail(jwtService
                .getUsername(authHeader
                        .substring(7))).get().getId();
    }

    private boolean isProductExist(int id){
        return productRepository.existsById(id);
    }

}
