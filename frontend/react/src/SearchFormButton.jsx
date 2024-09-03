import React, {useState} from 'react';
import {
    Button,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    FormControl,
    FormLabel,
    Input,
    Stack,
    useDisclosure,
    Box,
    Spinner,
    Text,
    Wrap,
    WrapItem,
    Divider,
    Heading,
    Flex,
    Icon,
} from '@chakra-ui/react';
import {SearchIcon} from '@chakra-ui/icons';
import {getCustomerById, searchCustomersByParameter} from './services/client.js';
import {errorNotification} from "./services/notification.js";
import CardWithImage from "./components/customer/CustomerCard.jsx";
import {BiRefresh} from "react-icons/bi";

const SearchFormButton = () => {
    const {isOpen, onOpen, onClose} = useDisclosure();
    const [step, setStep] = useState('select');
    const [searchParameter, setSearchParameter] = useState('');
    const [formData, setFormData] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [showResults, setShowResults] = useState(false); // Changed from hasSearched

    const handleParameterClick = (parameter) => {
        setSearchParameter(parameter);
        setStep('input');
    };

    const handleChange = (e) => {
        setFormData(e.target.value);
    };

    const handleSubmit = async () => {
        setError('');
        setLoading(true);
        setShowResults(true);
        try {
            // Search for customers by parameter
            const results = await searchCustomersByParameter(searchParameter, formData);
            console.log("Search results:", results);

            // Extract customer IDs from the search results
            const customerIds = results.map(customer => customer.id);

            // Fetch full customer details for each ID
            const fullCustomerDetails = await Promise.all(customerIds.map(id => getCustomerById(id)));
            console.log("Full customer details:", fullCustomerDetails);

            // Update the search results state with the full details
            setSearchResults(fullCustomerDetails);

            // Close the modal but keep results visible
            onClose();
        } catch (e) {
            setError('Search failed. Please try again.');
            console.error("Search error:", e);
            errorNotification(e.code, e.message);
        } finally {
            setLoading(false);
        }
    };

    const handleRefresh = () => {
        setSearchResults([]);
        setShowResults(false); // Hide results section when refreshing
    };

    const resetForm = () => {
        setSearchParameter('');
        setFormData('');
        setStep('select');
    };

    return (
        <>
            <Button
                rightIcon={<SearchIcon/>}
                colorScheme="blue"
                onClick={() => {
                    resetForm();
                    onOpen();
                }}
                top="3"
                left="67%"
            >
                Search
            </Button>
            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay/>
                <ModalContent bg="gray.300" color="black">
                    <ModalHeader>{step === 'select' ? 'Select a search parameter' : `Search by ${searchParameter}`}</ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody>
                        {step === 'select' ? (
                            <Stack spacing={5} maxW={350} marginX={6}>
                                <Button onClick={() => handleParameterClick('name')} colorScheme="blue">
                                    Search by Name
                                </Button>
                                <Button onClick={() => handleParameterClick('age')} colorScheme="blue">
                                    Search by Age
                                </Button>
                                <Button onClick={() => handleParameterClick('email')} colorScheme="blue">
                                    Search by Email
                                </Button>
                            </Stack>
                        ) : (
                            <Stack spacing={4} marginTop={6}>
                                <FormControl id={searchParameter}>
                                    <FormLabel>
                                        {searchParameter.charAt(0).toUpperCase() + searchParameter.slice(1)}:
                                    </FormLabel>
                                    <Input
                                        name={searchParameter}
                                        value={formData}
                                        onChange={handleChange}
                                        placeholder={`Enter ${searchParameter}`}
                                    />
                                </FormControl>
                            </Stack>
                        )}
                    </ModalBody>
                    <ModalFooter>
                        {step === 'select' ? (
                            <Button isDisabled size={0}></Button>
                        ) : (
                            <>
                                <Button colorScheme="blue" mr={3} onClick={handleSubmit} isLoading={loading}>
                                    Search
                                </Button>
                                <Button variant="ghost" onClick={resetForm}>
                                    Back to Selection
                                </Button>
                                <Button variant="ghost" onClick={onClose}>
                                    Cancel
                                </Button>
                            </>
                        )}
                    </ModalFooter>
                </ModalContent>
            </Modal>
            <Box p={4}>
                {showResults && (
                    <>
                        <Divider my="7" borderColor="black" borderWidth={5}/>
                        <Flex
                            direction="column"
                            align="center"
                            justify="center"
                        >
                            <Heading mb={1}>Search results: {searchResults.length}</Heading>
                            <Flex align="center" justify="center" gap={2}>
                                <Text textAlign="center">
                                    Click to remove search results
                                </Text>
                                <Icon as={BiRefresh} boxSize={8} onClick={handleRefresh}/>
                            </Flex>
                        </Flex>
                        {error && <Text color="red.500">{error}</Text>}
                        {loading ? (
                            <Spinner/>
                        ) : (
                            searchResults.length > 0 && (
                                <Wrap justify={"center"} spacing={"30px"} mt={4}>
                                    {searchResults.map((customer) => (
                                        <WrapItem key={customer.id}>
                                            <CardWithImage
                                                {...customer}
                                                imageNumber={customer.id} // Adjusted to use customer.id
                                                fetchCustomers={() => {
                                                }}
                                            />
                                        </WrapItem>
                                    ))}
                                </Wrap>
                            )
                        )}
                        <Divider my="7" borderColor="black" borderWidth={5}/>
                    </>
                )}
            </Box>
        </>
    );
};

export default SearchFormButton;