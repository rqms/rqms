package com.rqms.service;

import java.util.List;
import java.util.Set;

public interface CRUDService {
	<T> List<T> getAll(Class<T> klass);

	<T> void Save(T klass);

	<T> void delete(T klass);

	<T> T GetUniqueEntityByNamedQuery(String query, Object... params);
	<T> void BatchSave(Set<T> klass);
	<T> void Batchdelete(Set<T> klass);
}
