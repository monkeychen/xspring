package org.xspring.tutorial.guava.ext;

import com.google.common.base.MoreObjects;

/**
 * <p>Title: User</p>
 * <p>Description:</p>
 * <p>Copyright: Ruijie Co., Ltd. (c) 2018</p>
 * <p>@Author: chenzhian </p>
 * <p>@Date: 2018/8/16 上午11:23</p>
 */
public class User {
    private String name;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("email", email)
                .toString();
    }
}
