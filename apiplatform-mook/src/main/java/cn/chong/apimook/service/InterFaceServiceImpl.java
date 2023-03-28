package cn.chong.apimook.service;

import cn.chong.common.model.dto.SearchRequest;
import cn.chong.common.model.vo.SearchVo;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--23 19:10
 * @description
 */
@Service
public class InterFaceServiceImpl implements InterfaceService{
    @Override
    public String getPictureUrl(SearchRequest searchRequest) {
        String searchText = searchRequest.getSearchText();
        String url = String.format("https://cn.bing.com/images/search?q=%s&first=%s", searchText, 1);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Elements elements = doc.select(".iuscp.isv");

        List<SearchVo> pictures = new ArrayList<>();
        for (Element element : elements) {
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");
            // 取标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
            SearchVo picture = new SearchVo();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictures.add(picture);
        }

        Gson gson = new Gson();
        String jsonStr = gson.toJson(pictures);
        return jsonStr;
    }
}
