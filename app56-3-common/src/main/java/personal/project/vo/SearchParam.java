package personal.project.vo;

import com.google.common.util.concurrent.Striped;

public class SearchParam {
    private String SearchType;
    private String SearchKeyword;

    public String getSearchType() {
        return SearchType;
    }

    public void setSearchType(String searchType) {
        SearchType = searchType;
    }

    public String getSearchKeyword() {
        return SearchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        SearchKeyword = searchKeyword;
    }

    @Override
    public String toString() {
        return "SearchParam{" +
                "SearchType='" + SearchType + '\'' +
                ", SearchKeyword='" + SearchKeyword + '\'' +
                '}';
    }
}
