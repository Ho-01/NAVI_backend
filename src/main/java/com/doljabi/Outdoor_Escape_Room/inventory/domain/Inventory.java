package com.doljabi.Outdoor_Escape_Room.inventory.domain;

import com.doljabi.Outdoor_Escape_Room.common.error.AppException;
import com.doljabi.Outdoor_Escape_Room.common.error.GlobalErrorCode;
import com.doljabi.Outdoor_Escape_Room.item.domain.Item;
import com.doljabi.Outdoor_Escape_Room.run.domain.Run;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inventory",
        uniqueConstraints = @UniqueConstraint(columnNames = {"run_id", "item_id"})
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

    private int itemCount;

    @Builder
    public Inventory(Run run, Item item, int itemCount){
        this.run = run;
        this.item = item;
        this.itemCount = itemCount;
    }

    public void update(Operation operation, int count) {
        if(operation==Operation.ADD){
            this.itemCount += count;
        } else if (operation==Operation.REMOVE&&itemCount-count>0) {
            this.itemCount -= count;
        } else if (operation==Operation.SET&&count>=0) {
            this.itemCount = count;
        } else {
            throw new AppException(GlobalErrorCode.INVALID_STATE);
        }
    }
}
