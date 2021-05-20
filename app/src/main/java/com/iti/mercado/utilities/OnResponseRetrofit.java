package com.iti.mercado.utilities;

import java.util.List;

public interface OnResponseRetrofit<K> {
    void onResponse(List<K> items);
}
