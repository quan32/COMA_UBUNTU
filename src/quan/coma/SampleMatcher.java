package quan.coma;

import de.wdilab.ml.impl.MappingEntry;
import de.wdilab.ml.impl.Similarity;
import de.wdilab.ml.impl.matcher.simple.AbstractSimpleOneSourceAttributeObjectMatcher;
import de.wdilab.ml.interfaces.mapping.IMappingProvider;
import de.wdilab.ml.interfaces.mapping.IMappingStore;
import de.wdilab.ml.interfaces.mapping.MappingStoreException;
import de.wdilab.ml.interfaces.matcher.IAttributeObjectMatcher;
import de.wdilab.ml.interfaces.oi.IObjectInstance;
import de.wdilab.ml.interfaces.oi.IObjectInstanceProvider;

public class SampleMatcher extends AbstractSimpleOneSourceAttributeObjectMatcher implements IAttributeObjectMatcher {
	public SampleMatcher( final String attr1, final String attr2, final float threshold) {
		super( attr1, attr2, threshold);
	}
	@Override
	public void match(IObjectInstanceProvider oip1, IObjectInstanceProvider oip2,
			IMappingStore mrs) throws MappingStoreException{
		for( IObjectInstance oiLeft : oip1) {
			for( IObjectInstance oiRight : oip2) {
				float similarity = calculateSimilarity( oiLeft, oiRight);
				if( similarity < threshold) {
					continue;
				}
				else if( similarity <= 1) {
					mrs.add(new MappingEntry(oiLeft, oiRight, new
							Similarity( similarity)));
				}
				else{
					mrs.add( new MappingEntry( oiLeft, oiRight, new Similarity(0)));
				}
			}
		}
	}
	/**
	 * Calculates the similarity (sample method)
	 * @paramleft The left element
	 * @paramright The right element
	 * @returnThe similarity.
	 */
	private float calculateSimilarity( IObjectInstance left, IObjectInstance right) {
		String valueLeft = left.getValue( attrLinks).toString();
		String valueRight = right.getValue( attrRechts).toString();
		System.out.println("Chu y:");
		if(isNumber(valueLeft) && isNumber(valueRight)){
			float sim = 0.0f;
			float a= Float.parseFloat(valueLeft);
			float b=Float.parseFloat(valueRight);
			float ab = a-b;
			ab = Math.abs(a-b);
			if(a==0)
				sim = 0;
			else{
				sim = 1.0f - (float)ab/a;
				if(sim < 0)
					sim =0;
			}
			System.out.println( valueLeft + " <-> "+ valueRight+" : "+sim);  // Sample Output
			return sim;
		}
		else{
			System.out.println( valueLeft + " <-> "+ valueRight);  // Sample Output
			if( valueLeft.equals( valueRight)) {
				return 1;
			} else{
				return 0;
			}
		}
	}
	
	
	public static boolean isNumber(String s) {
	    try { 
	        Float.parseFloat(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	@Override
	public void match(IMappingProvider iMappingEntries, IMappingStore iMappingStore) throws MappingStoreException {
	}
}