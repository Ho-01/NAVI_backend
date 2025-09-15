package com.doljabi.Outdoor_Escape_Room.inventory.domain;

import com.doljabi.Outdoor_Escape_Room.item.domain.Item;
import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "inventory",
        uniqueConstraints = @UniqueConstraint(name = "uk_inventory_run_item", columnNames = {"run_id","item_id"})
)
public class Inventory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "run_id", nullable = false)
    private Run run;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "item_count", nullable = false)
    private int itemCount;

    @Builder
    public Inventory(Run run, Item item, int itemCount){
        this.run = run;
        this.item = item;
        this.itemCount = Math.max(0, itemCount);
    }

    // 변경 메서드
    public void setItemCount(int count) { this.itemCount = Math.max(0, count); }
    public void addItemCount(int delta) { this.itemCount = Math.max(0, this.itemCount + delta); }
}
