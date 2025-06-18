package com.hospitals.forms;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Doctorform {
  private String name;
    private String specialization;
    private String hospitalname;
    private Long hospitalId;
}
