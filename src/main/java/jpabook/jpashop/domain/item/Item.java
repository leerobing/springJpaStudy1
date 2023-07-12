package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    protected Long id;

    protected String name;
    protected int price;
    protected int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    /**
     제고 증가 로직
     * */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
    /**
     제고 증가 로직
     * */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    /**
     * 업데이트 시 수정 로직
    * */
    public Item change(Item param) {
        this.id = param.getId();
        this.name = param.getName();
        this.price = param.getPrice();
        this.stockQuantity = getStockQuantity();
        return this;
    }
}
