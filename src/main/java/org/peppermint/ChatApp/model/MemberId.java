package org.peppermint.ChatApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberId implements Serializable {
    private String user;
    private String room;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberId that = (MemberId) o;
        return Objects.equals(user, that.user) && Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, room);
    }
}
