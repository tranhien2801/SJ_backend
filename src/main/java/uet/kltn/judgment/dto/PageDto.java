package uet.kltn.judgment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDto extends ResponseDto{
    @JsonProperty("total_page")
    private int totalPage;
    @JsonProperty("size")
    private int size;
    @JsonProperty("total")
    private Long total;
    @JsonProperty("page")
    private int page;

    public PageDto(Object data, int size, Long total, int page) {
        super(data);
        this.totalPage = (int) (total % (long) size > 0 ? total / (long) size + 1 : total / (long) size);
        this.size = size;
        this.total = total;
        this.page = page;
    }

    public PageDto(int size, Long total, int page) {
        this.totalPage = (int) (total % (long) size > 0 ? total / (long) size + 1 : total / (long) size);
        this.size = size;
        this.total = total;
        this.page = page;
    }
}
