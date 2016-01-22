package org.gesis.charmstats.view.Graph;

import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.VertexView;

/**
 * 
 * @author	Martin Friedrichs
 * @since	0.8.1
 *
 */
public class GCellViewFactory  extends DefaultCellViewFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see org.jgraph.graph.DefaultCellViewFactory#createVertexView(java.lang.Object)
	 */
	protected VertexView createVertexView(Object cell) {
		if (cell instanceof DimensionCell) {
			return new DimensionView (cell);
		} else if (cell instanceof SpecificationCell) {
			return new SpecificationView (cell);
		} else if (cell instanceof IndicatorCell) {
			return new IndicatorView (cell);
		} else if(cell instanceof VariableCell) {
			return new VariableView (cell);
		} else if(cell instanceof CategoryCell) {
			return new CategoryView (cell);
		} else if(cell instanceof ValueCell) {
			return new ValueView (cell);
		} else if(cell instanceof MeasurementCell) {
			return new MeasurementView (cell);	
		} else if(cell instanceof EllipseCell) {
			return new EllipseView (cell);
		} else if (cell instanceof RoundedCell) {
			return new RoundedView (cell);
		} else if (cell instanceof LabelCell) {
			return new RoundedView (cell);
		}
		return new VertexView (cell);
	}

}
