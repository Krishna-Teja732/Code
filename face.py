def isPrime(n):
    max = int(n**0.5)+1
    for cur in range(2,max):
        if n%cur==0:
            return False
    
    return True

def reverseNumber(n):
    result=0
    while(n>0):
        result =result*10+n%10
        n = n//10
    return result

def check(n):
    if(n==-1):
        return False
    return True

def sieveOfEratosthenes(n):
    nums = [num for num in range(2,n)]
    max = int((n-1)**0.5)+1
    size = len(nums)
    for ind in range(0,max):
        curNum = nums[ind]
        if curNum == -1:
            continue
        for cur in range(ind+1,size):
            if(nums[cur]%curNum==0):
                nums[cur]=-1
    return list(filter(check, nums))
        

def azadAndChocolates(remAmount: int)-> int:
    amtSpent = 100- remAmount
    if amtSpent<3:
        return 0
    if amtSpent%7==0 or amtSpent%3==0:
        return 1
    if azadAndChocolates(remAmount+7)==1 or azadAndChocolates(remAmount+3)==1:
        return 1
    return 0


def findFactors(num: int):
    res = list()
    max = int(num**0.5)+1
    for factor in range(1,max):
        if(num%factor==0):
            res.append(factor)
            res.append(int(num/factor))

    return res

def sumOfPrimefactors(num: int):
    res,max=0,int(num//2)+1
    for cur in range(2,max):
        if(num%cur==0):
            res+=cur
            while(num%cur==0):
                num=int(num/cur)
    if res==0:
        res = num
    return res
    

def primeFactors(num: int):
    res,max,temp = list(),int(num//2)+1,num
    for cur in range(2,max):
        while(num%cur==0):
            res.append(cur)
            num=int(num/cur)
    if len(res)==0:
        res.append(temp)
    return res
    
def toggleBulbs(num):
    return int(num**0.5)

def factorial(num):
    if num==0 or num==1:
        return 1
    return num*factorial(num-1)

def sumOfAllPermuntations(nums):
    sum,carry,size,result = 0,0,len(nums),0
    for num in nums:
        sum+=num
    sum = sum*factorial(size-1)
    while(size>0):
        result = result+((sum+carry)%10)*pow(10,len(nums)-size)
        carry = (sum+carry)//10
        size = size-1
    return pow(10,len(nums))*carry + result
     
    

if __name__=='__main__':
    size = int(input())
    nums = list()
    for ind in range(0,size):
        nums.append(int(input()))
    print(sumOfAllPermuntations(nums))