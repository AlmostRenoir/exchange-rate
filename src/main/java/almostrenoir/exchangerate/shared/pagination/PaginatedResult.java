package almostrenoir.exchangerate.shared.pagination;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResult<T> {
    private final List<T> content;
    private final int totalPages;
}
