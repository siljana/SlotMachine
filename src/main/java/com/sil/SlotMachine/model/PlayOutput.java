package com.sil.SlotMachine.model;

import com.sil.SlotMachine.business.SlotMachineMatrix;
import com.sil.SlotMachine.business.Winline;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
@Schema(description = "Play result.")
public class PlayOutput implements Serializable {

    @Schema(description = "Randomly selected subsets from the 3 reels, resulting in a matrix.")
    private SlotMachineMatrix slotsSelection;
    @Schema(description = "The found winlines of 3 same elements aligned horizontally or diagonally (or combination " +
            "from it).")
    private List<Winline> winlines;
    @Schema(description = "The calculated win amount from all winlines in a play.")
    private Float winTotalAmount;
    @Schema(description = "Win currency.")
    private String winCurrency;

}
