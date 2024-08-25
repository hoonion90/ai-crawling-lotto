package dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfo {
    private String storeName;
    private String location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreInfo storeInfo = (StoreInfo) o;
        return Objects.equals(storeName, storeInfo.storeName) &&
               Objects.equals(location, storeInfo.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeName, location);
    }
}