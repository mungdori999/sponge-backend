package com.mungdori.sponge.domain.trainer;

import com.mungdori.sponge.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static java.util.Objects.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class History extends AbstractEntity {

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "start_dt", nullable = false, length = 50)
    private String startDt;

    @Column(name = "end_dt", length = 50)
    private String endDt;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    public static History create(HistoryCreateRequest createRequest, Trainer trainer) {
        History history = new History();

        history.title = requireNonNull(createRequest.title());
        history.startDt = requireNonNull(createRequest.startDt());
        history.endDt = createRequest.endDt();
        history.description = requireNonNull(createRequest.description());

        history.trainer = trainer;

        trainer.getHistoryList().add(history);

        return history;
    }
}
