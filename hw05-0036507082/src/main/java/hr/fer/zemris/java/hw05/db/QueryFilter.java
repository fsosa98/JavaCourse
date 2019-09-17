package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Class QueryFilter represents filter for querry.
 * 
 * @author Filip
 */
public class QueryFilter implements IFilter {
	
	private List<ConditionalExpression> list;

	/**
	 * Constructor.
	 * 
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression querry : list) {
			if  (!querry.getComparisonOperator().satisfied(querry.getFieldGetter().get(record), querry.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}
	
}
