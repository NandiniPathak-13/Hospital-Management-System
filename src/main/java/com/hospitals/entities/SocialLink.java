package com.hospitals.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity(name = "links")
@Table(name = "links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialLink {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private String link;
 private String title;

@ManyToOne
@JoinColumn(name = "hospital_id")
private Hospital hospital;

}
