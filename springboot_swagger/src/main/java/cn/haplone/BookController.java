package cn.haplone;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by z on 17-3-27.
 */
@RestController
@RequestMapping("books")
public class BookController {

        @RequestMapping(method = RequestMethod.GET)
        @ApiOperation(value = "获取全部数据", notes = "简单接口描述 userName必填", code = 200, produces = "application/json")
        public List<BookBean> getAll(){
                List<BookBean> list = new ArrayList<BookBean>(10);
                return list;
        }
}
