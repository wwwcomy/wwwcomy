package com.iteye.wwwcomy.llk;

public class DefaultReachableStrategy implements IReachable {

	@Override
	public boolean reach(Card[][] cards, Card c1, Card c2) {
		boolean result = false;
		if (c1.getValue() != c2.getValue())
			return false;
		else {
			int c1x = c1.getX();
			int c1y = c1.getY();
			int c2x = c2.getX();
			int c2y = c2.getY();
			result = judgeSameRowOrCol(cards, c1x, c1y, c2x, c2y);
			if (!result)
				result = judgeSingleAngle(cards, c1x, c1y, c2x, c2y);
		}
		return result;
	}

	private boolean judgeSingleAngle(Card[][] cards, int c1x, int c1y, int c2x, int c2y) {
		boolean c1xBigger = (c1x >= c2x ? true : false);
		boolean c1yBigger = (c1y >= c2y ? true : false);
		if (c1xBigger && c1yBigger) {
			return (judgeRow(cards, c2x + 1, c1x + 1, c2y) && judgeCol(cards, c2y + 1, c1y, c1x))
					|| (judgeCol(cards, c2y + 1, c1y + 1, c2x) && judgeRow(cards, c2x, c1x, c1y));
		} else if (!c1xBigger && !c1yBigger) {
			return (judgeRow(cards, c1x + 1, c2x + 1, c1y) && judgeCol(cards, c1y + 1, c2y, c2x))
					|| (judgeCol(cards, c1y + 1, c2y + 1, c1x) && judgeRow(cards, c1x, c2x, c2y));
		} else if (c1xBigger && !c1yBigger) {
			return (judgeRow(cards, c2x + 1, c1x + 1, c2y) && judgeCol(cards, c1y + 1, c2y + 1, c1x))
					|| (judgeCol(cards, c1y, c2y, c2x) && judgeRow(cards, c2x, c1x - 1, c1y));
		} else if (!c1xBigger && c1yBigger) {
			return (judgeRow(cards, c1x + 1, c2x + 1, c1y) && judgeCol(cards, c2y + 1, c1y + 1, c2x))
					|| (judgeCol(cards, c2y, c1y, c1x) && judgeRow(cards, c1x, c2x - 1, c2y));
		}
		return false;
	}

	private boolean judgeSameRowOrCol(Card[][] cards, int c1x, int c1y, int c2x, int c2y) {
		if (c1x == c2x) {
			boolean bigger = (c1y >= c2y ? true : false);
			if (!bigger) {
				int tmp = c1y;
				c1y = c2y;
				c2y = tmp;
			}
			return judgeRow(cards, c2y + 1, c1y, c1x);
		}
		if (c1y == c2y) {
			boolean bigger = (c1x >= c2x ? true : false);
			if (!bigger) {
				int tmp = c1x;
				c1x = c2x;
				c2x = tmp;
			}
			return judgeCol(cards, c2x + 1, c1x, c1y);
		}
		return false;
	}

	private boolean judgeRow(Card[][] cards, int smallX, int largeX, int y) {
		if (smallX > largeX)
			throw new UnsupportedOperationException("Wrong args");
		for (int i = smallX; i < largeX; i++) {
			if (cards[i][y] != null)
				return false;
		}
		return true;
	}

	private boolean judgeCol(Card[][] cards, int smallY, int largeY, int x) {
		if (smallY > largeY)
			throw new UnsupportedOperationException("Wrong args");
		for (int i = smallY; i < largeY; i++) {
			if (cards[x][i] != null)
				return false;
		}
		return true;
	}
}
