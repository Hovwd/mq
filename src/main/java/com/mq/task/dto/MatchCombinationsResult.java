package com.mq.task.dto;

import lombok.Data;

import java.util.List;

@Data
public class MatchCombinationsResult {
   private List<MatchCombination> combinationList;
   private MatchCombination result;
}
