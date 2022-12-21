package com.example.weeklygrind;

import java.util.List;

public class OrderBlock {

    private List<String> OrderList;

    public OrderBlock() {
    }

    public OrderBlock(List<String> orderList) {
        OrderList = orderList;
    }

    public List<String> getOrderList() {
        return OrderList;
    }

    public void setOrderList(List<String> orderList) {
        OrderList = orderList;
    }
}
