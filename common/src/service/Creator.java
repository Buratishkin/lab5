package service;

import interfaces.Identifiable;

public interface Creator<T extends Comparable<T> & Identifiable>{
    int create();
}
