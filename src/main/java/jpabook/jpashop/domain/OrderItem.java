package jpabook.jpashop.domain;

import jpabook.jpashop.domain.Item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
// 생성 메소드로만 orderItem을 생성할 수 있도록 제한 2
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @GeneratedValue
    @Id
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    private int orderPrice; // 주문 당시 가격
    private int count; // 주문 당시 수량

    // 생성 메소드로만 orderItem을 생성할 수 있도록 제한 1
    //    protected OrderItem(){
    //
    //    }


   // 생성 메소드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        item.removeStock(count);
        return orderItem;
    }

    // 비즈니스 로직 //
    // 1. 주문 취소
    public void cancel() {
        getItem().addStock(count);

    }

    // 2. 전체 주문 가격
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
