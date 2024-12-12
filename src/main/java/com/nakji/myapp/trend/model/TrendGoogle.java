package com.nakji.myapp.trend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.sql.Timestamp;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name="trend_google")
@Check(constraints = "rank > 1 AND rank < 20 AND is_active IN (0,1) ")
public class TrendGoogle {

    @Id
    @Column(name="trend_google_id")
    private Long trendGoogleId;

    @Column(name="collected_date")
    private LocalDate collectedDate;

    @Column(name="keyword_name")
    private String keywordName;

    @Column(name="reg_Date")
    private Timestamp regDate;

    @Column(name="update_date")
    private Timestamp updateDate;

    @Column(name="country")
    private String country;

    @Column(name="img_url")
    private String imgUrl;

    @Column(name="article_url")
    private String articleUrl;

    @Column(name="rank")
    private int rank;

    @Column(name="is_active")
    private int isActive;
}