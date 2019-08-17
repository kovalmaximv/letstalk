package NeuroActivity.letstalk.domain;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "thinksDB")
public class Think extends Post {
}
