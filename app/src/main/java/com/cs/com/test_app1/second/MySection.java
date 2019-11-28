package com.cs.com.test_app1.second;

import com.chad.library.adapter.base.entity.SectionEntity;

public class MySection extends SectionEntity<User> {

    public MySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MySection(User user) {
        super(user);
    }
}
