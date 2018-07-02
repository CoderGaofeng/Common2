package com.prayxiang.support.common.vo;

import com.prayxiang.support.recyclerview.tools.Cell;

public class Space implements Cell {
    public int space = 10;
    public int type;

    public Space(int type, int space) {
        this.type = type;
        this.space = space;
    }

    public Space(int type) {
        this.type = type;
    }

    public Space() {
        super();
    }


    @Override
    public int getItemViewType() {
        return type;
    }
}
