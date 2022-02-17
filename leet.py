class Solution(object):
    def checkIfExist(self, arr):
        vals = set()
        for i in arr:
            if i in vals:
                return True
            vals.add(i*2)
            if i%2==0: vals.add(i//2)
        return False
    
    def numberOfSubarrays(self, nums, k):
        countOdds,result,size = [0],0,len(nums)+1
        for num in nums:
            if num%2!=0:
                countOdds.append(countOdds[-1]+1)
            else:
                countOdds.append(countOdds[-1])
        if countOdds[-1]<k: return 0
        for subarrayLenght in range(k,size):
            for arrayEndInd in range(subarrayLenght,size):
                if countOdds[arrayEndInd]-countOdds[arrayEndInd-subarrayLenght]==k: result+=1
        return result

if __name__ == '__main__':
    nums=list(map(int,input()[1:-1].split(',')))
    k = int(input())
    print((Solution().numberOfSubarrays(nums,k)))