package com.nghex.exe202.service;

import com.nghex.exe202.entity.Product;
import com.nghex.exe202.entity.User;
import com.nghex.exe202.entity.Wishlist;
import com.nghex.exe202.exception.WishlistNotFoundException;

public interface WishlistService {
    Wishlist createWishlist(User user);
    Wishlist getWishlistByUserId(User user);
    Wishlist addProductToWishlist(User user, Product product) throws WishlistNotFoundException;

}

