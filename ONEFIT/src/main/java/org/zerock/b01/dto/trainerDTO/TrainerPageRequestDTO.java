package org.zerock.b01.dto.trainerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerPageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 20;

    @Builder.Default
    private int maxPages = 10;

    private String[] filters;
    private String sorting;

    @Builder.Default
    private int ordering = 0;

    public String getSorting() {
        if (this.sorting == null || sorting.isEmpty()) {
            return "trainerId";
        }
        return this.sorting;
    }

    public Pageable getPageable(String ...props) {
        if (this.ordering >= 0) {
            return PageRequest.of(this.page -1, this.size, Sort.by(props).ascending());
        }
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    private String link;

    public String getLink() {
        if (link == null) {
            StringBuilder builder = new StringBuilder();
            builder.append("page=").append(this.page).append("&size=").append(this.size);

            if (filters != null) {
                for (String filter : filters) {
                    builder.append("&filters=").append(filter);
                }
            }

            if (sorting != null) {
                builder.append("&sorting=").append(sorting);
            }

            builder.append("&ordering=").append(ordering);

            link = builder.toString();
        }
        return link;
    }
}
