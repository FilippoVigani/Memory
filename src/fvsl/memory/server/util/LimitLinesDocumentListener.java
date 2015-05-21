package fvsl.memory.server.util;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

// TODO: Auto-generated Javadoc
/*
 *  A class to control the maximum number of lines to be stored in a Document
 *
 *  Excess lines can be removed from the start or end of the Document
 *  depending on your requirement.
 *
 *  a) if you append text to the Document, then you would want to remove lines
 *     from the start.
 *  b) if you insert text at the beginning of the Document, then you would
 *     want to remove lines from the end.
 */
/**
 * The listener interface for receiving limitLinesDocument events. The class
 * that is interested in processing a limitLinesDocument event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addLimitLinesDocumentListener<code> method. When
 * the limitLinesDocument event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see LimitLinesDocumentEvent
 */
public class LimitLinesDocumentListener implements DocumentListener {
	private int maximumLines;
	private boolean isRemoveFromStart;

	/*
	 * Specify the number of lines to be stored in the Document. Extra lines
	 * will be removed from the start of the Document.
	 */
	/**
	 * Instantiates a new limit lines document listener.
	 * 
	 * @param maximumLines
	 *            the maximum lines
	 */
	public LimitLinesDocumentListener(int maximumLines) {
		this(maximumLines, true);
	}

	/*
	 * Specify the number of lines to be stored in the Document. Extra lines
	 * will be removed from the start or end of the Document, depending on the
	 * boolean value specified.
	 */
	/**
	 * Instantiates a new limit lines document listener.
	 * 
	 * @param maximumLines
	 *            the maximum lines
	 * @param isRemoveFromStart
	 *            the is remove from start
	 */
	public LimitLinesDocumentListener(int maximumLines, boolean isRemoveFromStart) {
		setLimitLines(maximumLines);
		this.isRemoveFromStart = isRemoveFromStart;
	}

	/*
	 * Return the maximum number of lines to be stored in the Document
	 */
	/**
	 * Gets the limit lines.
	 * 
	 * @return the limit lines
	 */
	public int getLimitLines() {
		return maximumLines;
	}

	/*
	 * Set the maximum number of lines to be stored in the Document
	 */
	/**
	 * Sets the limit lines.
	 * 
	 * @param maximumLines
	 *            the new limit lines
	 */
	public void setLimitLines(int maximumLines) {
		if (maximumLines < 1) {
			String message = "Maximum lines must be greater than 0";
			throw new IllegalArgumentException(message);
		}

		this.maximumLines = maximumLines;
	}

	// Handle insertion of new text into the Document

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void insertUpdate(final DocumentEvent e) {
		// Changes to the Document can not be done within the listener
		// so we need to add the processing to the end of the EDT

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				removeLines(e);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	/*
	 * Remove lines from the Document when necessary
	 */
	private void removeLines(DocumentEvent e) {
		// The root Element of the Document will tell us the total number
		// of line in the Document.

		Document document = e.getDocument();
		Element root = document.getDefaultRootElement();

		while (root.getElementCount() > maximumLines) {
			if (isRemoveFromStart) {
				removeFromStart(document, root);
			} else {
				removeFromEnd(document, root);
			}
		}
	}

	/*
	 * Remove lines from the start of the Document
	 */
	private void removeFromStart(Document document, Element root) {
		Element line = root.getElement(0);
		int end = line.getEndOffset();

		try {
			document.remove(0, end);
		} catch (BadLocationException ble) {
			System.out.println(ble);
		}
	}

	/*
	 * Remove lines from the end of the Document
	 */
	private void removeFromEnd(Document document, Element root) {
		// We use start minus 1 to make sure we remove the newline
		// character of the previous line

		Element line = root.getElement(root.getElementCount() - 1);
		int start = line.getStartOffset();
		int end = line.getEndOffset();

		try {
			document.remove(start - 1, end - start);
		} catch (BadLocationException ble) {
			System.out.println(ble);
		}
	}
}
