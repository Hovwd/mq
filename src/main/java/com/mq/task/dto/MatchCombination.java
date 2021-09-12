package com.mq.task.dto;

import lombok.Data;
import java.util.List;

@Data
public class MatchCombination {
    private List<Pair> pairList;
    private int averageMatchPercentage;
}
