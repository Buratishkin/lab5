package io;

import interfaces.Identifiable;

public interface InputManager<T extends Comparable<T> & Identifiable> {
    T inputObject();

    DataReader getDataReader();

    void setCustomId(int id);
}
