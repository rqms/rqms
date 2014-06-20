package com.rqms.dao;

import java.util.List;
import java.util.Set;

public interface CRUDDao {
	<T> List<T> getAll(Class<T> klass);

	<T> void Save(T klass);

	<T> T GetUniqueEntityByNamedQuery(String query, Object... params);
	
	<T> void delete(T klass);
	<T> void BatchSave(Set<T> klass);
	<T> void Batchdelete(Set<T> klass);
}
