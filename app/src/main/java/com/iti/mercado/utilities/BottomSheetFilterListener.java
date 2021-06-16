package com.iti.mercado.utilities;


import com.iti.mercado.model.Item;

import java.util.HashSet;
import java.util.List;

public interface BottomSheetFilterListener{

    void onApplyFilterClicked(HashSet<String>filterValuesBrands,List<Double>filterValuesPrice,
                              int flagLessChecked);
}
