package mo.wall.org.nestedrecyclerview.fragment;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import mo.wall.org.R;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-06 22:11
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NestedChildMultiItemQuickAdapter extends BaseMultiItemQuickAdapter<NestedChildMultiItemEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NestedChildMultiItemQuickAdapter(List<NestedChildMultiItemEntity> data) {
        super(data);
        addItemType(1, R.layout.activity_nested_recyclerview_title);
        addItemType(2, R.layout.activity_nested_recyclerview_mid_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, NestedChildMultiItemEntity item) {
        switch (item.getItemType()) {
            case 2:
                helper.setText(R.id.title, item.title);
                break;
        }
    }
}
