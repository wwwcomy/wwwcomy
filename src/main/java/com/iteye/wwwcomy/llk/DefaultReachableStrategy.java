package com.iteye.wwwcomy.llk;

import java.util.ArrayList;
import java.util.List;

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
			// 同行同列
			result = judgeSameRowOrCol(cards, c1x, c1y, c2x, c2y);
			if (!result)
				// 单个转角
				result = judgeSingleAngle(cards, c1x, c1y, c2x, c2y);
			if (!result)
				// 双转角
				result = judgeTwoAngle(cards, c1x, c1y, c2x, c2y);
		}
		return result;
	}

	private boolean judgeTwoAngle(Card[][] cards, int c1x, int c1y, int c2x, int c2y) {
		// 水平延伸,交集为可纵向检查连通的点
		List<Integer> blankHC1List = getHorizontalIndex(cards, c1x, c1y);
		List<Integer> blankHC2List = getHorizontalIndex(cards, c2x, c2y);
		blankHC1List.retainAll(blankHC2List);
		if (blankHC1List.size() > 0) {
			if (blankHC1List.remove(Integer.valueOf(-1)) || blankHC1List.remove(Integer.valueOf(CardHolder.size)))
				return true;
			boolean bigger = (c1y >= c2y ? true : false);
			if (!bigger) {
				int tmp = c1y;
				c1y = c2y;
				c2y = tmp;
			}
			for (Integer x : blankHC1List) {
				if (judgeCol(cards, c2y, c1y + 1, x))
					return true;
			}
		}

		// 垂直延伸,交集为可横向检查连通的点
		List<Integer> blankVC1List = getVerticalIndex(cards, c1x, c1y);
		List<Integer> blankVC2List = getVerticalIndex(cards, c2x, c2y);
		blankVC1List.retainAll(blankVC2List);
		if (blankVC1List.size() > 0) {
			if (blankVC1List.remove(Integer.valueOf(-1)) || blankVC1List.remove(Integer.valueOf(CardHolder.size)))
				return true;
			boolean bigger = (c1x >= c2x ? true : false);
			if (!bigger) {
				int tmp = c1x;
				c1x = c2x;
				c2x = tmp;
			}
			for (Integer y : blankVC1List) {
				if (judgeRow(cards, c2x, c1x + 1, y))
					return true;
			}
		}
		return false;
	}

	/**
	 * 获取纵向相连接的空点
	 * 
	 * @param cards
	 * @param x
	 * @param y
	 * @return
	 */
	private List<Integer> getVerticalIndex(Card[][] cards, int x, int y) {
		List<Integer> result = new ArrayList<Integer>();
		if (y == 0)
			result.add(-1);
		if (y == CardHolder.size - 1)
			result.add(CardHolder.size);
		for (int i = y - 1; i >= 0; i--) {
			if (cards[x][i] == null) {
				result.add(i);
				if (i == 0)
					result.add(-1);
			} else
				break;
		}

		for (int i = y + 1; i < CardHolder.size; i++) {
			if (cards[x][i] == null) {
				result.add(i);
				if (i == CardHolder.size - 1)
					result.add(CardHolder.size);
			} else
				break;
		}
		return result;
	}

	/**
	 * 获取横向可连接的空点
	 * 
	 * @param cards
	 * @param x
	 * @param y
	 * @return
	 */
	private List<Integer> getHorizontalIndex(Card[][] cards, int x, int y) {
		List<Integer> result = new ArrayList<Integer>();
		if (x == 0)
			result.add(-1);
		if (x == CardHolder.size - 1)
			result.add(CardHolder.size);
		for (int i = x - 1; i >= 0; i--) {
			if (cards[i][y] == null) {
				result.add(i);
				if (i == 0)
					result.add(-1);
			} else
				break;
		}

		for (int i = x + 1; i < CardHolder.size; i++) {
			if (cards[i][y] == null) {
				result.add(i);
				if (i == CardHolder.size - 1)
					result.add(CardHolder.size);
			} else
				break;
		}
		return result;
	}

	/**
	 * 单转角，四个点形成一个矩形，判断两个边是不是连通即可
	 * 
	 * @param cards
	 * @param c1x
	 * @param c1y
	 * @param c2x
	 * @param c2y
	 * @return
	 */
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

	/**
	 * 判断从smallX到largeX-1这一行区间是不是空的，如果是空的则表示是可连通的
	 * 
	 * @param cards
	 * @param smallX
	 * @param largeX
	 * @param y
	 * @return
	 */
	private boolean judgeRow(Card[][] cards, int smallX, int largeX, int y) {
		if (smallX > largeX)
			throw new UnsupportedOperationException("Wrong args");
		for (int i = smallX; i < largeX; i++) {
			if (cards[i][y] != null)
				return false;
		}
		return true;
	}

	/**
	 * 判断从smallY到largeY-1这一列区间是不是空的，如果是空的则表示是可连通的
	 * 
	 * @param cards
	 * @param smallY
	 * @param largeY
	 * @param x
	 * @return
	 */
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
