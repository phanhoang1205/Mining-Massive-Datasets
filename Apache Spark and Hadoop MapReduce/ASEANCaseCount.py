import getpass
from pyspark import SparkContext, SparkConf

username = getpass.getuser()

conf = SparkConf() \
        .setAppName("ASEAN Cumulative Cases Count")

sc = SparkContext(conf=conf)

URI = f"hdfs://localhost:9000/user/{username}/lab04/input/WHO-COVID-19-20210601-213841.tsv"
dataFile = sc.textFile(URI).cache()

def process(line):
    fields = line.split('\t')
    country = fields[1]
    if country == 'South-East Asia':
        cases = int(float(fields[2].replace(',', '')))
        return ("ASEAN", cases)
    else:
        return ("ASEAN", 0)

# Process the data
result = dataFile.map(process)

cumulative_cases = result.reduceByKey(lambda a, b: a + b)

# Collect the results
final_result = cumulative_cases.collect()

# Print the results
for country, cases in final_result:
    print(f'{country}: {cases}')