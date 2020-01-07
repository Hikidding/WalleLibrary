package mo.wall.org.nestedrecyclerview;

import android.os.Bundle;

import java.util.Arrays;

/**
 * Copyright (C), 2018-2020
 * Author: ziqimo
 * Date: 2020-01-06 20:38
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class NestedRecyclerViewPresenter extends NestedRecyclerViewContract.Presenter {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getView() != null) {
            getView().showData(Arrays.asList(
                    new NestedMultiItemEntity.Builder().type(1).build(),

                    new NestedMultiItemEntity.Builder().type(3).build(),

                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),

                    new NestedMultiItemEntity.Builder().type(3).build(),

                    new NestedMultiItemEntity.Builder().type(4).build(),
                    new NestedMultiItemEntity.Builder().type(4).build(),
                    new NestedMultiItemEntity.Builder().type(4).build(),
                    new NestedMultiItemEntity.Builder().type(4).build(),
                    new NestedMultiItemEntity.Builder().type(4).build(),
                    new NestedMultiItemEntity.Builder().type(4).build(),

                    new NestedMultiItemEntity.Builder().type(3).build(),

                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),
                    new NestedMultiItemEntity.Builder().type(2).build(),

                    new NestedMultiItemEntity.Builder().type(5).build()
            ));
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {

    }

    @Override
    protected void onPause() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onDestroy() {

    }
}