from sys import int_info


def nth_lowest_selling(sales, n):
    """
    :param elements: (list) List of book sales.
    :param n: (int) The n-th lowest selling element the function should return.
    :returns: (int) The n-th lowest selling book id in the book sales list.
    """
    max = 1
    for val in sales:
        if val > max:
            max = val
    count = [0]*(max)
    for sale in sales:
        count[sale-1]+=1
    visited = [0]*max
    min = 2
    for i in range(n):
        for ind in range(len(count)):
            if(visited[ind]!=-1 and count[ind]<count[min]):
                min = ind
        visited[min] = -1
    return min

if __name__ == "__main__":
    print(nth_lowest_selling([5, 4, 3, 2, 1, 5, 4, 3, 2, 5, 4, 3, 5, 4, 5], 2))