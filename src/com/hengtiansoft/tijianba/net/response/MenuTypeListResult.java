
package com.hengtiansoft.tijianba.net.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * com.hengtiansoft.tijianba.net.response.MenuTypeListResult
 * 
 * @author Qiuyan Wu qiuyanwu@hengtiansoft.com <br/>
 *         create at Nov 10, 2014 2:22:09 PM
 */
public class MenuTypeListResult extends ResponseResult {
    @SerializedName("data")
    private ArrayList<MenuType> menuTypes = new ArrayList<MenuType>();

    public class MenuType {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public ArrayList<MenuType> getMenuTypes() {
        return this.menuTypes;
    }

    public void setMenuTypes(ArrayList<MenuType> menuTypes) {
        this.menuTypes = menuTypes;
    }

}
